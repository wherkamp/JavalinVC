package me.kingtux.javalinvc.controller;


@FunctionalInterface
public interface ControllerExecutor {
    /**
     * The Execute method
     *
     * @throws ControllerExeception thrown when failing
     */
    void execute() throws ControllerExeception;
}
