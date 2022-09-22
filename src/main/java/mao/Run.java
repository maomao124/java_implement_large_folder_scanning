package mao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Project name(项目名称)：java实现大文件夹扫描
 * Package(包名): mao
 * Class(类名): Run
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/22
 * Time(创建时间)： 10:42
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Run
{
    /**
     * 排行榜大小
     */
    public static int size;

    /**
     * 工作路径
     */
    public static String path;

    /**
     * id
     */
    private static int id = 0;

    /**
     * 列表
     */
    private static List<FilePath> list;

    /**
     * 子路径
     */
    private static List<Path> subPaths;

    /**
     * 是否是正常结束
     */
    private static volatile boolean isNormalEnd;

    /**
     * 模式，1为扫描文件子路径
     */
    private static volatile int mode = 0;

    /**
     * 详细 1为更详细的输出
     */
    private static int detailed = 0;


    /**
     * 得到文件绝对路径
     *
     * @param path 工作目录
     * @return {@link List}<{@link FilePath}>
     */
    public static List<FilePath> getFiles_AbsolutePath(String path)
    {
        list = new ArrayList<>();
        getFiles_AbsolutePath(path, list);
        return list;
    }

    /**
     * 得到文件绝对路径
     *
     * @param clientBase 工作路径
     * @param list       列表
     */
    private static void getFiles_AbsolutePath(String clientBase, List<FilePath> list)
    {
        File file = new File(clientBase);
        // 如果这个路径是文件夹
        if (file.isDirectory())
        {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            if (files != null)
            {
                for (File value : files)
                {
                    // 如果还是文件夹 递归获取里面的文件 文件夹
                    if (value.isDirectory())
                    {
                        //继续读取文件夹里面的所有文件
                        getFiles_AbsolutePath(value.getPath(), list);
                    }
                    else
                    {
                        id++;
                        addList(list, id, value);
                    }
                }
            }
        }
        else
        {
            id++;
            addList(list, id, file);
        }
    }

    /**
     * 添加到列表
     *
     * @param list 列表
     * @param id   id
     * @param file file
     */
    private static void addList(List<FilePath> list, int id, File file)
    {
        if (file == null)
        {
            return;
        }
        FilePath filePath = new FilePath();
        filePath.setId(id);
        filePath.setSize(file.length());
        filePath.setPath(file.getPath());
        list.add(filePath);
        if (mode == 1)
        {
            return;
        }
        System.out.print("\r扫描到" + id + "个文件");
    }

    /**
     * 排序
     *
     * @param list 列表
     */
    private static void sort(List<FilePath> list)
    {
        list.sort(new Comparator<FilePath>()
        {
            @Override
            public int compare(FilePath o1, FilePath o2)
            {
                //return Long.compare(o2.getSize(), o1.getSize());
                if (o1.getSize() > o2.getSize())
                {
                    return -1;
                }
                else if (o1.getSize() < o2.getSize())
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }

    /**
     * 排序
     */
    private static void sort()
    {
        subPaths.sort(new Comparator<Path>()
        {
            @Override
            public int compare(Path o1, Path o2)
            {
                return Double.compare(o2.getFileTotalSizeMB(), o1.getFileTotalSizeMB());
            }
        });
    }

    /**
     * 显示
     *
     * @param list 列表
     */
    private static void show(List<FilePath> list)
    {
        System.out.println();
        System.out.println();
        System.out.println("+------------------------------------------------------->");
        System.out.println("|  序号  |   大小    |    文件路径");
        System.out.println("+------------------------------------------------------->");
        int loop;
        //Math.min(list.size(), size);
        if (list.size() > size)
        {
            loop = size;
        }
        else
        {
            loop = list.size();
        }
        for (int i = 0; i < loop; i++)
        {
            FilePath filePath = list.get(i);
            if (filePath.getSize() > 1024 * 1024)
            {
                double MSize = ((double) filePath.getSize()) / 1024.0 / 1024.0;
                String format = String.format("|%4d\t|%8.3fMB\t|   %s", (i + 1), MSize, filePath.getPath());
                System.out.println(format);
            }
            else
            {
                double KSize = ((double) filePath.getSize()) / 1024.0;
                String format = String.format("|%4d\t|%8.3fKB\t|   %s", (i + 1), KSize, filePath.getPath());
                System.out.println(format);
            }
        }
        System.out.println("+------------------------------------------------------->");
    }

    /**
     * 验证路径是否正确
     *
     * @param path 路径
     * @return boolean
     */
    private static boolean verifyPath(String path)
    {
        try
        {
            File file = new File(path);
            if (!file.exists())
            {
                return false;
            }
            if (!file.isDirectory())
            {
                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static void loadDetailed(String[] args)
    {
        try
        {
            int detailed = Integer.parseInt(args[2]);
            if (detailed != 0 && detailed != 1)
            {
                Run.detailed = 0;
            }
            if (detailed == 1)
            {
                Run.detailed = detailed;
                System.out.println("显示更详细的信息");
            }
        }
        catch (Exception e)
        {
            Run.detailed = 0;
        }
    }

    /**
     * 加载工作路径
     *
     * @param args 参数
     */
    private static void loadPath(String[] args)
    {
        try
        {
            String path = args[1];
            if (verifyPath(path))
            {
                Run.path = path;
            }
            else
            {
                Run.path = new File("").getAbsolutePath();
            }
        }
        catch (Exception e)
        {
            Run.path = new File("").getAbsolutePath();
        }
        System.out.println("工作路径：" + Run.path);
    }

    /**
     * 加载排行榜大小
     *
     * @param args 参数
     */
    private static void loadSize(String[] args)
    {
        try
        {
            int size = Integer.parseInt(args[0]);

            Run.size = size;
            System.out.println("排行最多显示" + size + "条");
        }
        catch (Exception e)
        {
            Run.size = 100;
            System.out.println("排行最多显示" + size + "条");
        }
    }

    /**
     * 计算总大小
     *
     * @param list 列表
     */
    private static void CalculateTotalSize(List<FilePath> list)
    {
        long totalSize = 0;
        for (FilePath filePath : list)
        {
            totalSize += filePath.getSize();
        }
        System.out.println();
        System.out.printf("所有文件加起来的总大小：%.4fMB\n", (((double) totalSize)) / 1024.0 / 1024.0);
    }


    /**
     * 得到总大小
     *
     * @param list 列表
     * @return long
     */
    private static long getTotalSize(List<FilePath> list)
    {
        long totalSize = 0;
        for (FilePath filePath : list)
        {
            totalSize += filePath.getSize();
        }
        return totalSize;
    }

    /**
     * 得到总大小,单位为mb
     *
     * @param list 列表
     * @return double
     */
    private static double getTotalSizeMB(List<FilePath> list)
    {
        return (((double) getTotalSize(list))) / 1024.0 / 1024.0;
    }

    /**
     * pwd路径所有的子路径
     *
     * @param pwdPath pwd路径
     * @return {@link List}<{@link String}>
     */
    private static List<String> getPwdPathAllSubPath(String pwdPath)
    {
        List<String> list = new ArrayList<>();
        File file = new File(pwdPath);
        File[] files = file.listFiles();
        if (files != null)
        {
            for (File file1 : files)
            {
                if (file1.exists() && file1.isDirectory())
                {
                    list.add(file1.getAbsolutePath());
                }
            }
        }
        return list;
    }

    /**
     * 初始化
     *
     * @param subPath 子路径
     */
    private static void initSubPath(List<String> subPath)
    {
        subPaths = new ArrayList<>();

        System.out.println("+------------------------------------------------------->");
        System.out.println("|  序号  |  子路径");
        System.out.println("+------------------------------------------------------->");

        for (int i = 0; i < subPath.size(); i++)
        {
            Path subP = new Path();
            subP.setPath(subPath.get(i));
            List<FilePath> subFiles = getFiles_AbsolutePath(subPath.get(i));
            subP.setFilePaths(subFiles);
            String format = String.format("|%4d\t|  %s", (i + 1), subPath.get(i));
            System.out.println(format);
            subPaths.add(subP);
            id = 0;
        }

        System.out.println("+------------------------------------------------------->");

    }


    private static void CalculateSubPath()
    {
        for (Path subPath : subPaths)
        {
            double fileTotalSizeMB = getTotalSizeMB(subPath.getFilePaths());
            subPath.setFileTotalSizeMB(fileTotalSizeMB);
        }
    }

    private static void show()
    {
        System.out.println("+------------------------------------------------------->");
        System.out.println("|  序号  | 文件数量 |    总大小    |  子路径");
        System.out.println("+------------------------------------------------------->");

        for (int i = 0; i < subPaths.size(); i++)
        {
            double fileTotalSizeMB = getTotalSizeMB(subPaths.get(i).getFilePaths());
            String format = String.format("|%5d\t|%6d\t|%9.3fMB\t|  %s", (i + 1), subPaths.get(i).getFilePaths().size(), fileTotalSizeMB, subPaths.get(i).getPath());
            System.out.println(format);
        }

        System.out.println("+------------------------------------------------------->");
    }

    /**
     * 设置关闭钩子
     *
     * @param main Thread
     */
    private static void setShutdownHook(Thread main)
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (isNormalEnd)
                {
                    return;
                }
                main.stop();
                System.out.println();
                System.out.println();
                System.out.println("强行停止扫描！！！ 正在统计...");
                System.out.println();
                if (mode == 1)
                {
                    CalculateSubPath();
                    sort();
                    showDetailed();
                    show();
                    countSize();
                }
                else if (mode == 0)
                {
                    sort(list);
                    show(list);
                    CalculateTotalSize(list);
                }
            }
        }));
    }

    /**
     * 显示详细
     */
    private static void showDetailed()
    {
        if (detailed == 1)
        {
            for (Path subPath1 : subPaths)
            {
                System.out.println("子路径：" + subPath1.getPath());
                sort(subPath1.getFilePaths());
                show(subPath1.getFilePaths());
                System.out.println();
                System.out.println();
                System.out.println();
            }

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("总输出");
            System.out.println();
        }
    }

    private static void countSize()
    {
        double count = 0;
        for (Path subPath : subPaths)
        {
            count = count + subPath.getFileTotalSizeMB();
        }
        System.out.println();
        System.out.println();
        System.out.printf("所有文件加起来的总大小：%.3fMB\n" , count);
    }

    public static void main(String[] args)
    {
        //System.out.println(Arrays.toString(args));

        Thread main = Thread.currentThread();

        System.out.println("第一个参数为排行榜最多显示的条数；第二个参数为工作目录；" +
                "第三个参数为是否为详细模式，为1则输出更多的信息");
        System.out.println();

        loadSize(args);

        loadPath(args);

        loadDetailed(args);

        System.out.println();

        setShutdownHook(main);

        List<String> subPath = getPwdPathAllSubPath(Run.path);

        if (subPath.size() == 0)
        {
            //没有子路径
            System.out.println("没有子路径！正在统计文件");
            List<FilePath> list = getFiles_AbsolutePath(Run.path);

            System.out.println();

            sort(list);

            show(list);

            CalculateTotalSize(list);

            isNormalEnd = true;
            return;
        }

        mode = 1;
        initSubPath(subPath);

        System.out.println();
        System.out.println();

        CalculateSubPath();
        sort();
        showDetailed();
        show();
        countSize();

        isNormalEnd = true;
    }
}
