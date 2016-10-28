package cn.lemene.boringlife.module;

/**
 * Project name: BoringLife
 * Package name: cn.lemene.boringlife.module
 * Created by snail on 10/28/16 22:07.
 * @version v1.0
 */

public class Summary {
    private String title;

    private String summary;

    public Summary(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
