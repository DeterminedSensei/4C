import codedraw.*;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        CodeDraw window = new CodeDraw(1500, 800);
        int x;
        int i;
        int seq = 0;
        TextFormat font = new TextFormat();
        font.setFontSize(50);
        font.setFontName("Arial");
        font.setTextOrigin(TextOrigin.CENTER);
        font.setBold(true);
        window.setTextFormat(font);
        drawgameboard(window);

        int[] sol = new int[20];
        while (true) {
            x = (int) (Math.random() * 4 + 1);
            for (i = 0; i < sol.length; i++) {
                if (sol[i] == 0) {
                    sol[i] = x;
                    seq++;
                    break;
                }
            }
            window.setColor(Color.WHITE);
            window.fillRectangle(5, 0, 250, 100);
            window.setColor(Color.BLACK);
            window.drawRectangle(5, 0, 250, 100);
            window.drawText(100, 50, "Level " + seq);
            if (i == sol.length) {
                endgame(window, sol, seq, true);
            }
            window.show(1000);
            for (i = 0; i < sol.length; i++) {
                if (sol[i] == 0) {
                    break;
                }
                blinkingLight(window, sol[i], 750);
            }
            if(!playerturn(window,seq, sol))
                seq = 0;
        }
    }
        private static boolean playerturn (CodeDraw window, int seq, int[] sol)
        {
            EventScanner eventsc = window.getEventScanner();
            MouseClickEvent currentclick;
            int xClick;
            int yClick;
            int seq2 = 0;
            while (seq2 < seq) {
                if (eventsc.hasMouseClickEvent()) {
                    currentclick = eventsc.nextMouseClickEvent();
                    xClick = currentclick.getX() - 750;
                    yClick = currentclick.getY() - 400;
                    while (Math.pow(xClick, 2) + Math.pow(yClick, 2) >= 90000) {

                        System.out.println(xClick);
                        System.out.println(yClick);
                        window.setColor(Palette.WHITE);
                        window.fillRectangle(320, 350, 860, 100);
                        window.setColor(Palette.BLACK);
                        window.drawRectangle(320, 350, 860, 100);
                        window.setColor(Palette.DARK_ORANGE);
                        window.drawText(750, 400, "Please Click on one of the Colours");
                        window.show(2000);
                        drawgameboard(window);
                        while (!eventsc.hasMouseClickEvent())
                            eventsc.nextEvent();
                        currentclick = eventsc.nextMouseClickEvent();
                        xClick = currentclick.getX() - 750;
                        yClick = currentclick.getY() - 400;
                    }
                    if (xClick >= 0 && yClick >= 0) {
                        blinkingLight(window, 1, 250);
                        if (sol[seq2] == 1)
                            seq2++;
                        else {
                            endgame(window, sol, seq, false);
                            return false;
                        }
                    } else if (xClick >= 0) {
                        blinkingLight(window, 4, 250);
                        if (sol[seq2] == 4)
                            seq2++;
                        else {
                            endgame(window, sol, seq, false);
                            return false;
                        }
                    } else if (yClick >= 0) {
                        blinkingLight(window, 2, 250);
                        if (sol[seq2] == 2)
                            seq2++;
                        else {
                            endgame(window, sol, seq, false);
                            return false;
                        }
                    } else {
                        blinkingLight(window, 3, 250);
                        if (sol[seq2] == 3)
                            seq2++;
                        else {
                            endgame(window, sol, seq, false);
                            return false;
                        }
                    }

                } else
                    eventsc.nextEvent();
            }
            return true;
        }
    private static void drawgameboard(CodeDraw window)

    {
        window.setColor(Palette.WHITE);
        window.fillRectangle(315,345,870,110);
        window.setColor(Color.BLACK);
        window.fillCircle(750, 400, 320);
        window.setColor(Color.BLUE);
        window.fillPie(750, 400, 300, 0, Math.PI / 2);
        window.setColor(Color.RED);
        window.fillPie(750, 400, 300, Math.PI / 2, Math.PI / 2);
        window.setColor(Color.GREEN);
        window.fillPie(750, 400, 300, Math.PI, Math.PI / 2);
        window.setColor(Color.YELLOW);
        window.fillPie(750, 400, 300, Math.PI / 2 * 3, Math.PI / 2);
        middlepoint(window);
        displayRecord(window);
        window.show();
    }
    private static void endgame(CodeDraw window, int[] sol, int seq, boolean win)
    {

        window.setColor(Palette.DARK_ORANGE);
        if(win) {
            window.drawText(750, 400, "YOU WON");
            window.show(5000);
        }
        else
        {
            window.drawText(750, 400, "GAME OVER");
            window.show(5000);
        }

        for (int i = 0; i < sol.length; i++)
        {
            sol[i] = 0;
        }
        try{
            Scanner rr;
            String pp = System.getProperty("user.dir");
            File rec = new File(pp+"\\record.txt");
            if(rec.createNewFile())
                System.out.println("record file created");
            else
                System.out.println("record file found");
            rr = new Scanner(rec);
            if(rr.hasNext()) {
                if (rr.nextInt() < seq) {
                    BufferedWriter newR = new BufferedWriter(new FileWriter(rec));
                    newR.write("" + seq);
                    newR.close();
                }
            }
            else
            {
                    BufferedWriter newR = new BufferedWriter(new FileWriter(rec));
                    newR.write("" + seq);
                    newR.close();
            }
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        drawgameboard(window);
    }
    private static void displayRecord(CodeDraw window)
    {
        BufferedReader rr;
        String pp = System.getProperty("user.dir");
        File rec = new File(pp+"\\record.txt");
        try{
            if(rec.createNewFile())
                System.out.println("record file created");
            else
                System.out.println("record file found");
            rr = new BufferedReader(new FileReader(rec));

            window.setColor(Color.WHITE);
            window.fillRectangle(5,600,350,100);
            window.setColor(Color.BLACK);
            window.drawRectangle(5,600,350,100);
            window.drawText(160,625, "Your Record");
            window.drawText(160, 675, "Level: " + rr.readLine());
        }

        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private static void middlepoint(CodeDraw window) {
        window.setColor(Color.BLACK);
        window.fillCircle(750, 400, 75);
    }


    private static void blinkingLight(CodeDraw window, int color, int time)
    {
        if (color == 1) {
        window.setColor(Palette.LIGHT_BLUE);
        window.fillPie(750, 400, 300, 0, Math.PI / 2);
        middlepoint(window);
        window.show(time);
        window.setColor(Color.BLUE);
        window.fillPie(750, 400, 300, 0, Math.PI / 2);
        middlepoint(window);
    } else if (color == 2) {
        window.setColor(Palette.LIGHT_CORAL);
        window.fillPie(750, 400, 300, Math.PI / 2, Math.PI / 2);
        middlepoint(window);
        window.show(time);
        window.setColor(Color.RED);
        window.fillPie(750, 400, 300, Math.PI / 2, Math.PI / 2);
        middlepoint(window);
    } else if (color == 3) {
        window.setColor(Palette.LIGHT_GREEN);
        window.fillPie(750, 400, 300, Math.PI, Math.PI / 2);
        middlepoint(window);
        window.show(time);
        window.setColor(Color.GREEN);
        window.fillPie(750, 400, 300, Math.PI, Math.PI / 2);
        middlepoint(window);
    } else {
        window.setColor(Palette.LIGHT_GOLDEN_ROD_YELLOW);
        window.fillPie(750, 400, 300, Math.PI / 2 * 3, Math.PI / 2);
        middlepoint(window);
        window.show(time);
        window.setColor(Color.YELLOW);
        window.fillPie(750, 400, 300, Math.PI / 2 * 3, Math.PI / 2);
        middlepoint(window);
    }
        window.show();


    }
    /*
    private static void drawStuff(int themen, int columns, CodeDraw window)
    {
        int boxwidth = (int)((1500 / themen) * 0.9);
        int spacesX = (int) (1500 - (1500 * 0.9)) / (themen + 1);
        int boxheight = (int) ((800 / columns) * 0.6);
        int spacesY = (int) (800 * 0.4);
        for (int i = 0;i < columns;i++)
        {

            for (int j = 0;j < themen; j++)
            {
                window.drawRectangle(spacesX*(j+1)+boxwidth*(j), boxheight*(i), boxwidth, boxheight);


            }
        }

    }*/
}
     