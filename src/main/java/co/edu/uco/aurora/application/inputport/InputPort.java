package co.edu.uco.aurora.application.inputport;

public interface InputPort <T, R> {
    R execute(T data);
}
