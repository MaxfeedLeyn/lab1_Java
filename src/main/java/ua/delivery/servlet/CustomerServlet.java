package ua.delivery.servlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.delivery.exception.AlreadyExistsException;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.Customer;
import ua.delivery.repository.CustomerRepository;
import ua.delivery.serializer.JsonDataSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customers", "/customers/"})
public class CustomerServlet extends BaseServlet {

    private JsonDataSerializer<Customer> serializer;
    private CustomerRepository customerRepository;

    @Override
    public void init() throws ServletException {
        logger.info("=== CustomerServlet init() ===");

        serializer = new JsonDataSerializer<>();

        customerRepository = (CustomerRepository) getServletContext()
                .getAttribute("customerRepository");

        if (customerRepository == null) {
            logger.error("CourseRepository not found in ServletContext");
            throw new ServletException("Application not properly initialized");
        }

        logger.info("CourseServlet initialized with {} courses", customerRepository.size());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType(CONTENT_TYPE_JSON);

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                handleGetWithFilters(req, resp);
            } else {
                String identity = decodePathParam(pathInfo.substring(1));
                handleGetByIdentity(identity, resp);
            }
        } catch (DataSerializationException e) {
            logger.error("Serialization error in doGet", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE_JSON);

        try {
            String requestBody = getRequestBody(req);
            Customer customer = serializer.fromString(requestBody, Customer.class);

            customerRepository.add(customer);
            logger.info("Customer created:");

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(serializer.toString(customer));

        } catch (AlreadyExistsException e) {
            logger.warn("Customer already exists: {}", e.getMessage());
            sendError(resp, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (InvalidDataException e) {
            logger.warn("Invalid customer data: {}", e.getMessage());
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (DataSerializationException e) {
            logger.error("Serialization error in doPost", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType(CONTENT_TYPE_JSON);

        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Customer identity is required");
            return;
        }

        String identity = decodePathParam(pathInfo.substring(1));

        try {
            String requestBody = getRequestBody(req);
            Customer updatedCourse = serializer.fromString(requestBody, Customer.class);

            boolean updated = customerRepository.update(updatedCourse);

            if (!updated) {
                logger.warn("Customer not found: {}", identity);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Customer not found: " + identity);
                return;
            }

            logger.info("Customer updated: {}", identity);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(serializer.toString(updatedCourse));

        } catch (DataSerializationException e) {
            logger.error("Serialization error in doPut", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Customer identity is required");
            return;
        }

        String identity = decodePathParam(pathInfo.substring(1));
        boolean removed = customerRepository.removeByIdentity(identity);

        if (!removed) {
            logger.warn("Customer not found for deletion: {}", identity);
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Customer not found: " + identity);
            return;
        }

        logger.info("Customer deleted: {}", identity);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private void handleGetWithFilters(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DataSerializationException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String group = req.getParameter("group");

        List<Customer> customers;

        if (firstName != null && !firstName.isBlank()) {
            customers = customerRepository.findByFirstName(firstName);
            logger.info("Filter by firsName '{}': {} customers", firstName, customers.size());
        } else if (lastName != null && !lastName.isBlank()) {
            customers = customerRepository.findByLastName(lastName);
            logger.info("Filter by lastName '{}': {} customers", lastName, customers.size());
        } else {
            customers = customerRepository.getAll();
            logger.info("Retrieved all {} customers", customers.size());
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(serializer.listToString(customers));
    }

    private void handleGetByIdentity(String identity, HttpServletResponse resp)
            throws IOException, DataSerializationException {
        Optional<Customer> customer = customerRepository.findByIdentity(identity);

        if (customer.isPresent()) {
            logger.info("Found customer: {}", identity);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(serializer.toString(customer.get()));
        } else {
            logger.warn("Customer not found: {}", identity);
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Customer not found: " + identity);
        }
    }

    private String decodePathParam(String param) {
        try {
            return java.net.URLDecoder.decode(param, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.warn("Failed to decode path param: {}", param);
            return param;
        }
    }

    @Override
    public void destroy() {
        logger.info("=== CustomerServlet destroy() ===");
        logger.info("Total requests processed: {}", getRequestCount());
        logger.info("Final customers count: {}",
                customerRepository != null ? customerRepository.size() : 0);
    }
}
