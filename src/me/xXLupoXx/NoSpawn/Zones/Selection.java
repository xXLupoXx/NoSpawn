/*
 * Copyright (c) <2012> <xXLupoXx>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.xXLupoXx.NoSpawn.Zones;

import me.xXLupoXx.NoSpawn.Util.NoSpawnDebugLogger;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Selection {
    private Vector min, max;
    public Vector pt1 = null, pt2 = null;
    public World w;

    public Selection() {
        min = new Vector(0, 0, 0);
        max = new Vector(0, 0, 0);
    }

    public Selection(Vector min, Vector max) {
        this.max = max;
        this.min = min;
    }

    public double getMinX() {
        return min.getX();
    }

    public double getMinY() {
        return min.getY();
    }

    public double getMinZ() {
        return min.getZ();
    }

    public double getMaxX() {
        return max.getX();
    }

    public double getMaxY() {
        return max.getY();
    }

    public double getMaxZ() {
        return max.getZ();
    }

    public void setMinX(double x) {
        min.setX(x);
    }

    public void setMinY(double y) {
        min.setY(y);
    }

    public void setMinZ(double z) {
        min.setZ(z);
    }

    public void setMaxX(double x) {
        max.setX(x);
    }

    public void setMaxY(double y) {
        max.setY(y);
    }

    public void setMaxZ(double z) {
        max.setZ(z);
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    public void createMinMax() {
        //Create Max Vector
        setMaxX(Math.max(pt1.getX(), pt2.getX()));
        setMaxY(Math.max(pt1.getY(), pt2.getY()));
        setMaxZ(Math.max(pt1.getZ(), pt2.getZ()));

        //Create Min Vector

        setMinX(Math.min(pt1.getX(), pt2.getX()));
        setMinY(Math.min(pt1.getY(), pt2.getY()));
        setMinZ(Math.min(pt1.getZ(), pt2.getZ()));

        //Check if MinY = MaxY because the effect won't show if so

        if (getMinY() == getMaxY()) {
            setMaxY(getMaxY() + 1);
        }

        NoSpawnDebugLogger.debugmsg("----Created Min/Max Vector----");
        NoSpawnDebugLogger.debugmsg("New Min X: "+min.getX()+" Y: "+min.getY()+" Z: "+min.getX());
        NoSpawnDebugLogger.debugmsg("New Max X: "+max.getX()+" Y: "+max.getY()+" Z: "+max.getX());
    }
}