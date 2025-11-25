package ua.delivery.service;

public record LoadResult(
        int customersLoaded,
        int ordersLoaded,
        long durationMs
) {
    public int getTotalItems(){
        return customersLoaded + ordersLoaded;
    }

    @Override
    public String toString(){
        return String.format(
                "LoadResult{customersLoaded=%d, ordersLoaded=%d. total=%d,  durationMs=%dms}",
                customersLoaded, ordersLoaded,
                getTotalItems(), durationMs
        );
    }
}
