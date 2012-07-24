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

package me.xXLupoXx.NoSpawn.Util;/*
* Copyright (C) 2011 Keyle & xXLupoXx
*
* This file is part of SpeechCraft.
*
* SpeechCraft is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* SpeechCraft is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with SpeechCraft. If not, see <http://www.gnu.org/licenses/>.
*/

import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;

import java.io.*;

public class NBTConfiguration
{
    private File NBTFile;
    private NBTTagCompound nbtTagCompound = new NBTTagCompound();

    public NBTConfiguration(String Path) {
        this(new File(Path));
    }

    public NBTConfiguration(File f) {
        NBTFile = f;
        if (!NBTFile.exists()) {
            try {
                NBTFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public NBTTagCompound getNBTTagCompound() {
        return nbtTagCompound;
    }

    public boolean save() {
        try {
            DataOutputStream F_Out = new DataOutputStream(new FileOutputStream(NBTFile));
            NBTBase.a(nbtTagCompound, F_Out);
            F_Out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void load() {
        try {
            FileInputStream fi = new FileInputStream(NBTFile);
            fi.read();

            if (fi.read() != -1) {
                fi.close();
                fi = new FileInputStream(NBTFile);
                DataInputStream F_In = new DataInputStream(fi);
                nbtTagCompound = (NBTTagCompound) NBTBase.b(F_In);
                F_In.close();
            }
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearConfig() {
        nbtTagCompound = new NBTTagCompound();
    }
}
