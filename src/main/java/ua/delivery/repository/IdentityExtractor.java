package ua.delivery.repository;

@FunctionalInterface
interface IdentityExtractor<T> {
    String extractIdentity(T object);
}