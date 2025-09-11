package infrastructure.config.handler;

import exception.EntidadeNaoEncontrada;
import exception.ValidacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntidadeNaoEncontrada.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada (EntidadeNaoEncontrada ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));

    }
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidacaoBean(MethodArgumentNotValidException ex){
        Map<String,String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->erros.put(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOutros(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }
}
