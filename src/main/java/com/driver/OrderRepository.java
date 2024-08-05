package com.driver;

import java.util.*;

public class OrderRepository {
    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, List<String>> partnerOrdersMap = new HashMap<>();
    private Map<String, String> orderPartnerMap = new HashMap<>();

    // Adding an order
    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    // Adding a delivery partner
    public void addPartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    // Assigning an order to a partner
    public void addOrderPartnerPair(String orderId, String partnerId) {
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)) {
            orderPartnerMap.put(orderId, partnerId);
            partnerOrdersMap.computeIfAbsent(partnerId, k -> new ArrayList<>()).add(orderId);
        }
    }

    // Getting an order by orderId
    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    // Getting a partner by partnerId
    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    // Getting the number of orders assigned to a partner
    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>()).size();
    }

    // Getting the list of orders assigned to a partner
    public List<Order> getOrdersByPartnerId(String partnerId) {
        List<Order> orders = new ArrayList<>();
        List<String> orderIds = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orderIds) {
            orders.add(orderMap.get(orderId));
        }
        return orders;
    }

    // Getting the list of all orders
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    // Getting the count of unassigned orders
    public int getCountOfUnassignedOrders() {
        int count = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderPartnerMap.containsKey(orderId)) {
                count++;
            }
        }
        return count;
    }

    // Getting the count of undelivered orders after a given time
    public int getCountOfUndeliveredOrdersAfterGivenTime(String time, String partnerId) {
        int count = 0;
        List<String> orderIds = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orderIds) {
            Order order = orderMap.get(orderId);
            if (order.getDeliveryTime().compareTo(time) > 0) {
                count++;
            }
        }
        return count;
    }

    // Getting the time of the last delivery by a partner
    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orderIds = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        String latestTime = "";
        for (String orderId : orderIds) {
            Order order = orderMap.get(orderId);
            if (order.getDeliveryTime().compareTo(latestTime) > 0) {
                latestTime = order.getDeliveryTime();
            }
        }
        return latestTime;
    }

    // Deleting a partner and unassigning their orders
    public void deletePartnerById(String partnerId) {
        List<String> orderIds = partnerOrdersMap.remove(partnerId);
        if (orderIds != null) {
            for (String orderId : orderIds) {
                orderPartnerMap.remove(orderId);
            }
        }
        partnerMap.remove(partnerId);
    }

    // Deleting an order and unassigning it from its partner
    public void deleteOrderById(String orderId) {
        String partnerId = orderPartnerMap.remove(orderId);
        if (partnerId != null) {
            List<String> orderIds = partnerOrdersMap.get(partnerId);
            if (orderIds != null) {
                orderIds.remove(orderId);
            }
        }
        orderMap.remove(orderId);
    }
}
