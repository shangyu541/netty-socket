package com.lagousocket.socketdemo.buffer;


import java.nio.ByteBuffer;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/8
 * @since 1.0.0
 */
public class bufferTest {


    public static void main(String[] args) {
        //createBufferDemo();
        //addBufferDemo();
        readBufferDemo();
    }

    /**
     * 缓冲区对象读取数据
     */
    private static void readBufferDemo() {
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("0123".getBytes());
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
        System.out.println("remaining:"+allocate.remaining());

        //切换读模式
        System.out.println("读取数据-------------");
        allocate.flip();
        //获取数据之前，需要调用flip方法
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
        System.out.println("remaining:"+allocate.remaining());
        for (int i = 0; i < allocate.limit(); i++) {
            System.out.println(allocate.get());
        }
        //读取完毕，继续读取会报错，超过limit值
        //读取指定索引字节
        System.out.println(allocate.get(1));

        //重复读取
        allocate.rewind();

        byte[] bytes = new byte[4];
        allocate.get(bytes);
        System.out.println(new String(bytes));

        //将缓冲区转化字节数组返回
        System.out.println("将缓冲区转化字节数组返回-----------");
        byte[] array = allocate.array();
        System.out.println(new String(array));

        //切换写模式，覆盖之前索引所在位置的值
        System.out.println("写模式------------");
        //再次写数据之前，需要调用clear方法，但是数据还未消失，等再次写入数据，被覆盖了才会消失
        allocate.clear();
        //通过abc替换了012
        allocate.put("abc".getBytes());
        System.out.println(new String(allocate.array()));
    }

    /**
     * 缓冲区对象添加数据
     */
    private static void addBufferDemo() {
        //创建一个指定长度的缓冲区，以byteBuffer为例
        ByteBuffer allocate = ByteBuffer.allocate(10);
        //获取当前索引所在位置
        System.out.println(allocate.position());
        //最多能操作到那个索引
        System.out.println(allocate.limit());
        //返回缓冲区总长度
        System.out.println(allocate.capacity());
        //还有多少个能操作
        System.out.println(allocate.remaining());

        ////修改当前索引位置
        //allocate.position(1);
        ////修改做多能操作到哪个索引位置
        //allocate.limit(9);
        ////获取当前索引所在位置
        //System.out.println(allocate.position());
        ////最多能操作到那个索引
        //System.out.println(allocate.limit());
        ////返回缓冲区总长度
        //System.out.println(allocate.capacity());
        ////还有多少个能操作
        //System.out.println(allocate.remaining());

        //添加一个字节
        allocate.put((byte) 97);
        //获取当前索引所在位置
        System.out.println(allocate.position());
        //最多能操作到那个索引
        System.out.println(allocate.limit());
        //返回缓冲区总长度
        System.out.println(allocate.capacity());
        //还有多少个能操作
        System.out.println(allocate.remaining());


        //添加一个字节数组
        allocate.put("abc".getBytes());
        //获取当前索引所在位置
        System.out.println(allocate.position());
        //最多能操作到那个索引
        System.out.println(allocate.limit());
        //返回缓冲区总长度
        System.out.println(allocate.capacity());
        //还有多少个能操作
        System.out.println(allocate.remaining());


        //当添加超过缓冲区的长度时会报错
        allocate.put("012345".getBytes());
        //获取当前索引所在位置
        System.out.println(allocate.position());
        //最多能操作到那个索引
        System.out.println(allocate.limit());
        //返回缓冲区总长度
        System.out.println(allocate.capacity());
        //还有多少个能操作
        System.out.println(allocate.remaining());
        //是否还能有操作的数组
        System.out.println(allocate.hasRemaining());

        //如果缓存区存满后，可以调整position位置可以重复写，这样会覆盖之前存入索引的对应的值
        allocate.position(0);
        allocate.put("012345".getBytes());

    }

    /**
     * 缓冲区对象创建
     */
    private static void createBufferDemo() {
        //创建一个指定长度的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            System.out.println("指定长度："+allocate.get());
        }
        ByteBuffer wrap = ByteBuffer.wrap("lagou".getBytes());
        for (int i = 0; i < 5; i++) {
            System.out.println("内容："+wrap.get());
        }
    }


}
