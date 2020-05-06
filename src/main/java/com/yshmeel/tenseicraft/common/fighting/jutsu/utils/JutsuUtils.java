package com.yshmeel.tenseicraft.common.fighting.jutsu.utils;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class JutsuUtils {
    // @todo странный метод, и вообще его бы переписать в будущем
    public static float[][] getRadiusCircle(World world, BlockPos blockPos) {
        float[][] positions = {
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
            {0,0,0},{0,0,0},{0,0,0},{0,0,0},
        };

        int i = 0;
        int iterations = 0;
        int distance = 0;
        boolean generated = false;
        int radius = 3;

        while(!generated) {
            positions[i][0] = blockPos.getX();
            positions[i][1] = blockPos.getY();
            positions[i][2] = blockPos.getZ();
            i++;

            positions[i][0] = blockPos.getX() + distance + 1;
            positions[i][1] = blockPos.getY();
            positions[i][2] = blockPos.getZ();
            i++;

            positions[i][0] = blockPos.getX() - distance - 1;
            positions[i][1] = blockPos.getY();
            positions[i][2] = blockPos.getZ();
            i++;

            positions[i][0] = blockPos.getX();
            positions[i][1] = blockPos.getY();
            positions[i][2] = blockPos.getZ() + distance + 1;
            i++;

            positions[i][0] = blockPos.getX();
            positions[i][1] = blockPos.getY();
            positions[i][2] = blockPos.getZ() - distance - 1;
            if(iterations >= 0) {
                positions[i][0] = blockPos.getX() - distance - 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() + distance + 1;
                i++;

                positions[i][0] = blockPos.getX() + distance + 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() + distance + 1;

                i++;
                positions[i][0] = blockPos.getX() + distance + 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() + distance + 1;

                i++;
                positions[i][0] = blockPos.getX() + distance + 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() - distance - 1;

                i++;
                positions[i][0] = blockPos.getX() - distance - 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() - distance - 1;

                distance++;
                i++;
                positions[i][0] = blockPos.getX() - distance - 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() - distance - 1;

                i++;
                positions[i][0] = blockPos.getX() - distance - 1;
                positions[i][1] = blockPos.getY();
                positions[i][2] = blockPos.getZ() - distance - 1;
            }
            distance ++;

            iterations++;
            if(iterations >= radius) {
                generated = true;
            }
        }
        return positions;
    }
    public static float[][][] getBestPositionToWall(World world, BlockPos pos) {
        BlockPos center = pos;

        float[][][] positions = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        };

        // top row
        positions[0][0][0] = center.getX() - 1;
        positions[0][0][1] = center.getY() + 3;
        positions[0][0][2] = center.getZ();

        positions[0][1][0] = center.getX();
        positions[0][1][1] = center.getY() + 3;
        positions[0][1][2] = center.getZ();

        positions[0][2][0] = center.getX() + 1;
        positions[0][2][1] = center.getY() + 3;
        positions[0][2][2] = center.getZ();
        // top row end

        // middle row
        positions[1][0][0] = center.getX() - 1;
        positions[1][0][1] = center.getY() + 2;
        positions[1][0][2] = center.getZ();

        positions[1][1][0] = center.getX();
        positions[1][1][1] = center.getY() + 2;
        positions[1][1][2] = center.getZ();

        positions[1][2][0] = center.getX() + 1;
        positions[1][2][1] = center.getY() + 2;
        positions[1][2][2] = center.getZ();
        // middle row end

        // bottom row
        positions[2][0][0] = center.getX() - 1;
        positions[2][0][1] = center.getY() + 1;
        positions[2][0][2] = center.getZ();

        positions[2][1][0] = center.getX();
        positions[2][1][1] = center.getY() + 1;
        positions[2][1][2] = center.getZ();

        positions[2][2][0] = center.getX() + 1;
        positions[2][2][1] = center.getY() + 1;
        positions[2][2][2] = center.getZ();
        // bottom row end

        return positions;
    }
}
