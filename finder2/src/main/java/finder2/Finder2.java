package finder2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ForkJoinPool;

public class Finder2 {

    public void start(int port) {
        var server = createHttpServer(port);
        server.createContext("/", this::handle);
        server.setExecutor(ForkJoinPool.commonPool());
        server.start();
    }

    private void handle(@NotNull HttpExchange exchange) throws IOException {
        try (var responseBody = exchange.getResponseBody()) {
            var body = "Finder2".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            responseBody.write(body);
        }
    }

    private static @NotNull HttpServer createHttpServer(int port) {
        try {
            var address = new InetSocketAddress(port);
            return HttpServer.create(address, 0);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
