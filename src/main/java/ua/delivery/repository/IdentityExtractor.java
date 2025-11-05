package ua.delivery.repository;

@FunctionalInterface
public interface IdentityExtractor<T> {
    String extractIdentity(T object);
}