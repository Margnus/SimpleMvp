package com.lxf.mvp.utils;

/**
 * Created by chaochen on 14-10-25.
 */
public class HtmlContent {

    private static final String REGX_PHOTO = "(?:<br>)? ?<a href=\"(?:[^\\\"]*)?\" (?:alt=\"\" )?target=\"_blank\" class=\"bubble-markdown-image-link\".*?><img src=\"(.*?)\" alt=\"(.*?)\".*?></a>(?:<br>)? ?";
    private static final String REGX_PHOTO_OLD = "<div class='message-image-box'><a href=\'(?:[^\\\']*)?\' target='_blank'><img class='message-image' src='(.*?)'/?></a></div>";
    private static final String REGX_PHOTO_IOS = "<div class=\"message-image-box\">(?:\\n)?(?:↵)? ?<a href=\"(?:.*)\" target=\"_blank\"(?: rel=\"(?:.*)\")?><img class=\"message-image\" src=\"(.*?)\"></a>(?:\\n)?(?:↵)? ?</div>";
    private static final String REPLACE_PHOTO = "[图片]";

    private static final String REGX_MONKEY = "<img class=\"emotion monkey\" src=\".*?\" title=\"(.*?)\">";
    private static final String REGX_EMOJI = "<img class=\"emotion emoji\" src=\".*?\" title=\"(.*?)\">";

    private static final String REGX_NET_PHOTO = "<img.*?alt=\"‘图片’\">";

    private static final String REGX_CODE = "(<pre>)?<code(.*\\n)*</code>(</pre>)?";

    private static final String REPLACE_CODE = "[代码]";

    private static final String REGX_HTML = "<([A-Za-z][A-Za-z0-9]*)[^>]*>(.*?)</\\1>";



    public static String parseReplacePhotoEmoji(String s) {
        return s.replaceAll(HtmlContent.REGX_PHOTO, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_OLD, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_IOS, REPLACE_PHOTO)
                .replaceAll(REGX_MONKEY, ":$1:")
                .replaceAll(REGX_EMOJI, ":$1:");
    }

    public static String parseReplaceHtml(String s) {
        return parseReplacePhotoEmoji(s).replaceAll("<[^>]*>", "");
    }

    public static String parseReplacePhotoMonkey(String s) {
        return s.replaceAll(REGX_MONKEY, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_OLD, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_IOS, REPLACE_PHOTO)
                .replaceAll(REGX_NET_PHOTO, REPLACE_PHOTO);
    }

    // 去除加粗字体样式
    public static String parseReplacePhotoMonkeySpecifyTitle(String s) {
        return parseReplacePhotoMonkey(s).replaceAll("</?h\\w>", "");
    }

    public static String parseToText(String s) {
        return s.replaceAll(REGX_MONKEY, "[$1]")
                .replaceAll(REGX_PHOTO, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_OLD, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_IOS, REPLACE_PHOTO)
                .replaceAll(REGX_CODE, REPLACE_CODE)
                .replaceAll(REGX_HTML, "$2")
                .replace("<sup>", "");
    }

    public static String parseToShareText(String s) {
        return s.replaceAll(REGX_MONKEY, "[$1]")
                .replaceAll(REGX_PHOTO, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_OLD, REPLACE_PHOTO)
                .replaceAll(REGX_PHOTO_IOS, REPLACE_PHOTO)
                .replaceAll(REGX_CODE, REPLACE_CODE)
                .replaceAll(REGX_HTML, "$2")
                .replace("<sup>", "")
                .replaceAll("<(.*?)>", "");
    }


    private static String replaceAllSpace(String s) {
        final String br = "<br>";
        s = s.replaceAll("<p>", "").replaceAll("</p>", br);
        if (s.endsWith(br)) {
            s = s.substring(0, s.length() - br.length());
        }

        if (s.startsWith(br)) {
            s = s.substring(br.length(), s.length());
        }

        String temp = s.replaceAll("<br>", "").replaceAll(" ", "").replaceAll("\n", "");
        if (temp.isEmpty()) {
            return "";
        }

//        s = s.replaceAll("( ?<br> ?)+", "<br>").replaceAll("( ?(<br>)? ?\n?)+$", "")
//                .replaceAll("^( ?(<br>)? ?\n?)+", "");

        s = s.replaceAll("( ?<br> ?)+", "<br>").replaceAll("( ?<br> ?\n?)+$", "").replaceAll("^( ?<br> ?\n?)+", "");
        return s;
    }

    public static String createUserHtml(String globalKey, String name) {
        final String format = "<font color='#3bbd79'><a href=\"/u/%s\">%s</a></font>";
        return String.format(format, globalKey, name);
    }
}
