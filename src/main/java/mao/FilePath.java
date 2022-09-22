package mao;

/**
 * Project name(项目名称)：java实现大文件夹扫描
 * Package(包名): mao
 * Class(类名): FilePath
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/22
 * Time(创建时间)： 10:42
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class FilePath
{
    //文件的序号
    private int id;
    //文件大小，单位为字节
    private long size;
    //文件绝对路径
    private String path;


    /**
     * Instantiates a new File path.
     */
    public FilePath()
    {
    }

    /**
     * Instantiates a new File path.
     *
     * @param id   the id
     * @param size the size
     * @param path the path
     */
    public FilePath(int id, long size, String path)
    {
        this.id = id;
        this.size = size;
        this.path = path;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public FilePath setId(int id)
    {
        this.id = id;
        return this;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public long getSize()
    {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     * @return the size
     */
    public FilePath setSize(long size)
    {
        this.size = size;
        return this;
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
    public FilePath setPath(String path)
    {
        this.path = path;
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

        FilePath filePath = (FilePath) o;

        if (getId() != filePath.getId())
        {
            return false;
        }
        if (getSize() != filePath.getSize())
        {
            return false;
        }
        return getPath() != null ? getPath().equals(filePath.getPath()) : filePath.getPath() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getId();
        result = 31 * result + (int) (getSize() ^ (getSize() >>> 32));
        result = 31 * result + (getPath() != null ? getPath().hashCode() : 0);
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString()
    {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("id：").append(id).append('\n');
        stringbuilder.append("size：").append(size).append('\n');
        stringbuilder.append("path：").append(path).append('\n');
        return stringbuilder.toString();
    }
}
