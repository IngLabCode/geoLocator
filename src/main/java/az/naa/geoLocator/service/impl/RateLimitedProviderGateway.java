package az.naa.geoLocator.service.impl;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.exception.RateLimitException;
import az.naa.geoLocator.service.GeolocationProvider;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class RateLimitedProviderGateway {


    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
    private final GeolocationProvider provider;

    public RateLimitedProviderGateway(GeolocationProvider provider) {
        this.provider = provider;
        startWorker();
    }

    public GeoResponse fetch(String ip) {
        CompletableFuture<GeoResponse> future = new CompletableFuture<>();
        queue.add(new Task(ip, future));
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RateLimitException("RateLimitedProviderGateway error", e);
        }
    }

    private void startWorker() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            Task task = queue.poll();
            if (task != null) {
                try {
                    GeoResponse geo = provider.getByIp(task.ip());
                    task.future().complete(geo);
                } catch (Exception ex) {
                    task.future().completeExceptionally(ex);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private record Task(String ip, CompletableFuture<GeoResponse> future) {
    }
}
