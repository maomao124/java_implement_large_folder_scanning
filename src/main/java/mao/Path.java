package mao;

import java.util.List;


/**
 * Project name(项目名称)：java实现大文件夹扫描
 * Package(包名): mao
 * Class(类名): Path
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/22
 * Time(创建时间)： 10:42
 * Version(版本): 1.0
 * Description(描述)： 无
 */


public class Path
{
    /**
     * 文件的子路径
     */
    private String path;

    /**
     * 路径下的所有文件
     */
    private List<FilePath> FilePaths;

    /**
     * 所有文件加起来的总大小
     */
    private double fileTotalSizeMB;

    /**
     * Instantiates a new Path.
     */
    public Path()
    {

    }

    /**
     * Instantiates a new Path.
     *
     * @param path            the path
     * @param filePaths       the file paths
     * @param fileTotalSizeMB the file total size mb
     */
    public Path(String path, List<FilePath> filePaths, double fileTotalSizeMB)
    {
        this.path = path;
        FilePaths = filePaths;
        this.fileTotalSizeMB = fileTotalSizeMB;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     * @return the path
     */
    public Path setPath(String path)
    {
        this.path = path;
        return this;
    }

    /**
     * Gets file paths.
     *
     * @return the file paths
     */
    public List<FilePath> getFilePaths()
    {
        return FilePaths;
    }

    /**
     * Sets file paths.
     *
     * @param filePaths the file paths
     * @return the file paths
     */
    public Path setFilePaths(List<FilePath> filePaths)
    {
        FilePaths = filePaths;
        return this;
    }

    /**
     * Gets file total size mb.
     *
     * @return the file total size mb
     */
    public double getFileTotalSizeMB()
    {
        return fileTotalSizeMB;
    }

    /**
     * Sets file total size mb.
     *
     * @param fileTotalSizeMB the file total size mb
     * @return the file total size mb
     */
    public Path setFileTotalSizeMB(double fileTotalSizeMB)
    {
        this.fileTotalSizeMB = fileTotalSizeMB;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Path path1 = (Path) o;

        if (Double.compare(path1.getFileTotalSizeMB(), getFileTotalSizeMB()) != 0)
        {
            return false;
        }
        if (getPath() != null ? !getPath().equals(path1.getPath()) : path1.getPath() != null)
        {
            return false;
        }
        return getFilePaths() != null ? getFilePaths().equals(path1.getFilePaths()) : path1.getFilePaths() == null;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getFilePaths() != null ? getFilePaths().hashCode() : 0);
        temp = Double.doubleToLongBits(getFileTotalSizeMB());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString()
    {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("path：").append(path).append('\n');
        stringbuilder.append("FilePaths：").append(FilePaths).append('\n');
        stringbuilder.append("fileTotalSizeMB：").append(fileTotalSizeMB).append('\n');
        return stringbuilder.toString();
    }
}
