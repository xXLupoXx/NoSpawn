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


import java.util.LinkedList;
import java.util.Queue;

public class PriorityZone
{
    Queue<Zone> Zones;

    public PriorityZone()
    {
         Zones = new LinkedList<Zone>();
    }

    public void insert(Zone z)
    {
        Zones.add(z);
    }

    public Zone getNextZone()
    {
        Zone tmp = Zones.peek();

        for(Zone z: Zones)
        {
            if(tmp.getPrio()<z.getPrio())
            {
                tmp = z;
            }
        }
        Zones.remove(tmp);

        return tmp;
    }

    public boolean isEmpty()
    {
        return Zones.isEmpty();
    }

    public int getNextPrio()
    {
        return Zones.size()+1;
    }
}


/*Comparator c = new Comparator<obj>( ) {

            public int compare(obj o1, obj o2)
            {
                if(1<2)
                {
                    return -1; // erstes object ist kleiner
                }
                else if(1==2)
                {
                    return 0; // Objekte sind gleich
                }
                else if(1>2)
                {
                    return 1; // zweites object ist kleiner
                }
                else {
                    return 0; // unwichtig
                }
            }
        };

        TreeSet<obj> bla = new TreeSet<obj>(c);
        */