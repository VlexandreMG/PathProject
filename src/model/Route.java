package model;

import java.util.*;

public class Route {
    double distance;
    double duration;
    Point startPoint;
    Point endPoint;
    List<Path> listPath;

    public Route(double distance, double duration, Point startPoint, Point endPoint, List<Path> listPath) {
        this.distance = distance;
        this.duration = duration;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.listPath = listPath;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public List<Path> getListPath() {
        return listPath;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setListPath(List<Path> listPath) {
        this.listPath = listPath;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

}