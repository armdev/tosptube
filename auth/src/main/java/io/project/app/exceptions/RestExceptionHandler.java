package io.project.app.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 *
 * @author armena
 */
@Component
@Order(-2)
@Slf4j
public class RestExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof BadRequestException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }else
        if (ex instanceof NotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }else{
            exchange.getResponse().setStatusCode(HttpStatus.EXPECTATION_FAILED);
            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }
        //return Mono.error(ex);
    }

}
