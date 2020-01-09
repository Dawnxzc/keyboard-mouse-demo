package com.xuzhichao.emulator;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Hello world
 */
public class App {

    public static void main( String[] args ) throws AWTException {

    }

    private Robot robot;

    /**
     * 获取鼠标所在位置
     */
    public EventEntity getMouseLocation() {
        PointerInfo info = MouseInfo.getPointerInfo();
        EventEntity entity = new EventEntity();
        entity.setMouseX(info.getLocation().x);
        entity.setMouseY(info.getLocation().y);
        return entity;
    }

    /**
     * 获取相应坐标的像素点颜色
     * @param x x 坐标
     * @param y y 坐标
     * @return
     */
    public Color getScreenPixel(int x, int y) {
        // 获取缺省工具包
        Toolkit tk = Toolkit.getDefaultToolkit();
        // 屏幕尺寸规格
        Dimension di = tk.getScreenSize();
        System.out.println(di.width);
        System.out.println(di.height);
        Rectangle rec = new Rectangle(0, 0, di.width, di.height);
        BufferedImage bi = robot.createScreenCapture(rec);
        int pixelColor = bi.getRGB(x, y);
        Color color=new Color(16777216 + pixelColor);
        // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
        return color;
    }

    /**
     * 鼠标移动
     * @param x
     * @param y
     */
    public void mouseMoveAndLeftClick(int x, int y){
        robot.mouseMove(x, y);
        robot.delay(1000);
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 按键
     */
    public void keyBoard() {
        if (null != robot) {
            robot.mousePress(MouseEvent.BUTTON1_MASK);
            robot.mouseRelease(MouseEvent.BUTTON1_MASK);

            robot.keyPress(KeyEvent.VK_A);
            robot.keyPress(KeyEvent.VK_9);
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_9);
        } else {
            System.out.println("请先初始化 Robot！");
        }
    }

    @Before
    public void before() throws AWTException {
        robot = new Robot();
    }

    @Test
    public void test01() {
        keyBoard();
    }

    @Test
    public void test02() throws InterruptedException {
        Thread.sleep(1000);
        EventEntity mouseLocation = getMouseLocation();
        System.out.println(mouseLocation);
        System.out.println(getScreenPixel(mouseLocation.getMouseX(), mouseLocation.getMouseY()));
    }

    @Test
    public void test03() throws InterruptedException {
       for (int i = 0; i < 100; i++) {
           // windows10 上面的鼠标移动函数有异常导致AWT此BUG，测试经过5次调用后正常
           robot.mouseMove(100, 100);
           robot.mouseMove(100, 100);
           robot.mouseMove(100, 100);
           robot.mouseMove(100, 100);
           robot.mouseMove(100, 100);
           System.out.println(getMouseLocation());
       }
    }

    @Test
    public void test04() {
        int x = 627;
        int y = 70;
        while (true) {
            robot.mouseMove(x, y);
            int mouseX = getMouseLocation().getMouseX();
            int mouseY = getMouseLocation().getMouseY();
            if (x == mouseX && y == mouseY) {
                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                robot.delay(1000);
                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                break;
            }
        }
    }



    /**
     * 事件实体对象
     */
    class EventEntity {
        private int mouseX;

        private int mouseY;

        public int getMouseX() {
            return mouseX;
        }

        public void setMouseX(int mouseX) {
            this.mouseX = mouseX;
        }

        public int getMouseY() {
            return mouseY;
        }

        public void setMouseY(int mouseY) {
            this.mouseY = mouseY;
        }

        @Override
        public String toString() {
            return "EventEntity{" +
                    "mouseX=" + mouseX +
                    ", mouseY=" + mouseY +
                    '}';
        }
    }
}
