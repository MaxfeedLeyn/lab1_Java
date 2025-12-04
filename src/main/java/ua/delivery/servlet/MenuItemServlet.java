package ua.delivery.servlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.delivery.exception.AlreadyExistsException;
import ua.delivery.exception.DataSerializationException;
import ua.delivery.exception.InvalidDataException;
import ua.delivery.model.MenuItem;
import ua.delivery.repository.MenuItemRepository;
import ua.delivery.serializer.JsonDataSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MenuItemServlet", urlPatterns = {"/menuitems", "/menuitems/"})
public class MenuItemServlet extends BaseServlet {

    private JsonDataSerializer<MenuItem> serializer;
    private MenuItemRepository menuItemRepository;

    @Override
    public void init() throws ServletException {
        logger.info("=== MenuItemServlet init() ===");

        serializer = new JsonDataSerializer<>();

        menuItemRepository = (MenuItemRepository) getServletContext()
                .getAttribute("menuItemRepository");

        if (menuItemRepository == null) {
            logger.error("menuItemRepository not found in ServletContext");
            throw new ServletException("Application not properly initialized");
        }

        logger.info("MenuItemServlet initialized with {} subjects", menuItemRepository.size());
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
                String name = decodePathParam(pathInfo.substring(1));
                handleGetByName(name, resp);
            }
        } catch (DataSerializationException e) {
            logger.error("Serialization error in doGet", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType(CONTENT_TYPE_JSON);

        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "MenuItem name is required");
            return;
        }

        String name = decodePathParam(pathInfo.substring(1));

        try {
            String requestBody = getRequestBody(req);
            MenuItem updatedSubject = serializer.fromString(requestBody, MenuItem.class);

            boolean updated = menuItemRepository.update(updatedSubject);

            if (!updated) {
                logger.warn("MenuItem not found: {}", name);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "MenuItem not found: " + name);
                return;
            }

            logger.info("MenuItem updated: {}", name);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(serializer.toString(updatedSubject));

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
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "MenuItem name is required");
            return;
        }

        String name = decodePathParam(pathInfo.substring(1));
        boolean removed = menuItemRepository.removeByIdentity(name);

        if (!removed) {
            logger.warn("MenuItem not found for deletion: {}", name);
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "MenuItem not found: " + name);
            return;
        }

        logger.info("MenuItem deleted: {}", name);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private void handleGetWithFilters(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DataSerializationException {

        String minParam = req.getParameter("min");
        String maxParam = req.getParameter("max");

        List<MenuItem> menuItems;

        if (minParam != null || maxParam != null) {
            try {
                int minCredits = minParam != null ? Integer.parseInt(minParam) : 1;
                int maxCredits = maxParam != null ? Integer.parseInt(maxParam) : 5;

                menuItems = menuItemRepository.findByPriceInRange(minCredits, maxCredits);
                logger.info("Filter by price range [{}-{}]: {} subjects",
                        minCredits, maxCredits, menuItems.size());
            } catch (NumberFormatException e) {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid price value: " + e.getMessage());
                return;
            }
        } else {
            menuItems = menuItemRepository.getAll();
            logger.info("Retrieved all {} subjects", menuItems.size());
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(serializer.listToString(menuItems));
    }

    private void handleGetByName(String name, HttpServletResponse resp)
            throws IOException, DataSerializationException {
        Optional<MenuItem> menuItem = menuItemRepository.findByIdentity(name);

        if (menuItem.isPresent()) {
            logger.info("Found menuItem: {}", name);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(serializer.toString(menuItem.get()));
        } else {
            logger.warn("menuItem not found: {}", name);
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "menuItem not found: " + name);
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
        logger.info("=== MenuItemServlet destroy() ===");
        logger.info("Total requests processed: {}", getRequestCount());
        logger.info("Final menuItems count: {}",
                menuItemRepository != null ? menuItemRepository.size() : 0);
    }
}
