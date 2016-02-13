package tree.note2note;

/**
 * Created by regina on 2/13/16.
 */
public class NoteInfo {
    private int mId;
    private String mName;
    private String mCourse;
    private String mOwner;

    public int getID(int id) {
        return id;
    }
    public void setID(int id) {
        mId = id;
    }

    public String getName(String name) {
        return name;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getCourse(String course) {
        return course;
    }
    public void setCourse(String course) {
        mCourse = course;
    }
    public String getOwner(String owner) {
        return owner;
    }
    public void setOwner(String owner) {
        mOwner = owner;
    }
}
