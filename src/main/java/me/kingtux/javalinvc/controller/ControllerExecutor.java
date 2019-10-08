package me.kingtux.javalinvc.controller;


@FunctionalInterface
public interface ControllerExecutor {
    void execute() throws ControllerExeception;
}
