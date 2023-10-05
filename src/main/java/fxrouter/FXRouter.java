package fxrouter;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import animatefx.animation.*;

public final class FXRouter {
    private static final String WINDOW_TITLE = "";
    private static final Double WINDOW_WIDTH = 1000.0D;
    private static final Double WINDOW_HEIGHT = 600.0D;
    private static final Double FADE_ANIMATION_DURATION = 800.0D;
    private static FXRouter router;
    private static Object mainRef;
    private static Stage window;
    private static String windowTitle;
    private static Double windowWidth;
    private static Double windowHeight;
    private static String animationType;
    private static Double animationDuration;
    private static AbstractMap<String, RouteScene> routes = new HashMap();
    private static RouteScene currentRoute;

    private FXRouter() {
    }

    public static void bind(Object ref, Stage win) {
        checkInstances(ref, win);
    }

    public static void bind(Object ref, Stage win, String winTitle) {
        checkInstances(ref, win);
        windowTitle = winTitle;
    }

    public static void bind(Object ref, Stage win, double winWidth, double winHeight) {
        checkInstances(ref, win);
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    public static void bind(Object ref, Stage win, String winTitle, double winWidth, double winHeight) {
        checkInstances(ref, win);
        // todo: Undecorated
        window.initStyle(StageStyle.UNDECORATED);
        windowTitle = winTitle;
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    private static void checkInstances(Object ref, Stage win) {
        if (mainRef == null) {
            mainRef = ref;
        }

        if (router == null) {
            router = new FXRouter();
        }

        if (window == null) {
            window = win;
        }

    }

    public static void when(String routeLabel, String scenePath) {
        RouteScene routeScene = new RouteScene(scenePath);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }


    public static void start(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent)FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.setTitle(route.windowTitle);
        window.setScene(new Scene(resource, route.sceneWidth, route.sceneHeight));
        window.centerOnScreen();
        window.show();
        new FadeInLeft(resource).play();
    }

    public static void goTo(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRoute(route);
    }

    public static void goTo(String routeLabel, double sceneWidth, double sceneHeight) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRoute(route, sceneWidth, sceneHeight);
    }

    public static void goTo(String routeLabel, Object data) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        loadNewRoute(route);
    }

    public static void goTo(String routeLabel, Object data, double sceneWidth, double sceneHeight) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        loadNewRoute(route, sceneWidth, sceneHeight);
    }

    public static void goTo(String routeLabel, Object data, Object dataII, double sceneWidth, double sceneHeight) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        route.dataII = dataII;
        loadNewRoute(route, sceneWidth, sceneHeight);
    }

    private static void loadNewRoute(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent)FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.setTitle(route.windowTitle);
        window.setScene(new Scene(resource, route.sceneWidth, route.sceneHeight));
        window.centerOnScreen();
        window.show();
        new FadeIn(resource).play();
    }

    private static void loadNewRoute(RouteScene route, double sceneWidth, double sceneHeight) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent)FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.setTitle(route.windowTitle);
        window.setScene(new Scene(resource, sceneWidth, sceneHeight));
        window.centerOnScreen();
        window.show();
        new FadeIn(resource).play();
    }

    public static void startFrom(String routeLabel) throws Exception {
        goTo(routeLabel);
    }

    public static void startFrom(String routeLabel, Object data) throws Exception {
        goTo(routeLabel, data);
    }

    public static void setAnimationType(String anType) {
        animationType = anType;
    }

    public static void setAnimationType(String anType, double anDuration) {
        animationType = anType;
        animationDuration = anDuration;
    }

    private static void routeAnimation(Parent node) {
        String anType = animationType != null ? animationType.toLowerCase() : "";
        byte var3 = -1;
        switch(anType.hashCode()) {
            case 3135100:
                if (anType.equals("fade")) {
                    var3 = 0;
                }
            default:
                switch(var3) {
                    case 0:
                        Double fd = animationDuration != null ? animationDuration : FADE_ANIMATION_DURATION;
                        FadeTransition ftCurrent = new FadeTransition(Duration.millis(fd), node);
                        ftCurrent.setFromValue(0.0D);
                        ftCurrent.setToValue(1.0D);
                        ftCurrent.play();
                    default:
                }
        }
    }
    private static void routeAnimationSwitch(Parent node) {
        new FadeIn(node).play();
    }

    public static Object getData() {
        return currentRoute.data;
    }

    public static Object getDataII() {
        return currentRoute.dataII;
    }

    private static class RouteScene {
        private String scenePath;
        private String windowTitle;
        private double sceneWidth;
        private double sceneHeight;
        private Object data;
        //
        private Object dataII;

        private RouteScene(String scenePath) {
            this(scenePath, getWindowTitle(), getWindowWidth(), getWindowHeight());
        }

        private RouteScene(String scenePath, String windowTitle) {
            this(scenePath, windowTitle, getWindowWidth(), getWindowHeight());
        }

        private RouteScene(String scenePath, double sceneWidth, double sceneHeight) {
            this(scenePath, getWindowTitle(), sceneWidth, sceneHeight);
        }

        private RouteScene(String scenePath, String windowTitle, double sceneWidth, double sceneHeight) {
            this.scenePath = scenePath;
            this.windowTitle = windowTitle;
            this.sceneWidth = sceneWidth;
            this.sceneHeight = sceneHeight;
        }

        private static String getWindowTitle() {
            return FXRouter.windowTitle != null ? FXRouter.windowTitle : "";
        }

        private static double getWindowWidth() {
            return FXRouter.windowWidth != null ? FXRouter.windowWidth : FXRouter.WINDOW_WIDTH;
        }

        private static double getWindowHeight() {
            return FXRouter.windowHeight != null ? FXRouter.windowHeight : FXRouter.WINDOW_HEIGHT;
        }
    }
}
