package br.com.henrique.rest;

import br.com.henrique.exception.PedidoNaoEncontradoException;
import br.com.henrique.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerRegraDeNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return  new ApiErrors(mensagemErro);

    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlerPedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerMethodNotValidException(MethodArgumentNotValidException ex){
       List<String> erros = ex.getBindingResult().getAllErrors().stream()
                .map(e ->e.getDefaultMessage()).collect(Collectors.toList());
       return new ApiErrors(erros);

    }

}
