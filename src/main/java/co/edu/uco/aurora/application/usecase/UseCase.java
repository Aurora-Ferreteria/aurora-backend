package co.edu.uco.aurora.application.usecase;

public interface UseCase <D, R>{
    R execute(D data);
}
