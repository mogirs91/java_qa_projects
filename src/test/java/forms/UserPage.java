package forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class UserPage extends Form {
    public UserPage() {
        super(By.xpath("//div[@class='ProfileHeader']"), "User page");
    }

    private final ILabel postMessage = getElementFactory().getLabel(By.xpath("//div[@id='page_wall_posts']//div[@class='wall_text']"), "Post message");
    private final ILink authorLink = getElementFactory().getLink(By.xpath("//div[@id='page_wall_posts']//div[@class='PostHeaderTitle']//a"), "Author link");
    private final ILink photoLink = getElementFactory().getLink(By.xpath("//div[@id='page_wall_posts']//div[@class='MediaGridContainerWeb--post']//a"), "Photo link");
    private final ILink replyAuthorLink = getElementFactory().getLink(By.xpath("//div[@id='page_wall_posts']//div[@class='replies']//div[@class='reply_author']/a"), "Reply author link");
    private final ILabel replyText = getElementFactory().getLabel(By.xpath("//div[@id='page_wall_posts']//div[@class='replies']//div[@class='reply_text']"), "Reply text");
    private final ILabel postLikeButton = getElementFactory().getLabel(By.xpath("//span[@class='PostBottomAction__icon _like_button_icon']"), "Post like button");
    private final Integer photoLinkSplitIndex = 1;
    private final String photoLinkSplitExpression = "_";
    private final String postDeletedLocator = "//div[@id='post%s_%s";

    public void postLikeButtonClick() {
        postLikeButton.click();
    }

    public String getPostMessage() {
        return postMessage.getText();
    }

    public String getAuthorLink() {
        return authorLink.getHref();
    }

    public String getPhotoLink() {
        int photoId = Integer.parseInt(photoLink.getHref().split(photoLinkSplitExpression)[photoLinkSplitIndex]);
        return Integer.toString(photoId);
    }

    public String getReplyAuthorLink() {
        return replyAuthorLink.getHref();
    }

    public String getReplyText() {
        return replyText.getText();
    }

    public boolean isPostDeleted(String userId, String postId) {
        return !getElementFactory().getLabel(By.xpath(String.format(postDeletedLocator, userId, postId)), "Post Id label").state().isExist();
    }
}
