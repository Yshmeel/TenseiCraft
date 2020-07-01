package com.yshmeel.chatmanager.api;

public class Channel {
    protected String name = null;
    protected String i18n = null;
    protected String preText = "";
    protected String tag = null;
    protected String description = null;
    protected String rules = null;
    protected double range = -1;
    protected boolean showChannelInfo = true;

    public String getName() {
        return name;
    }

    /**
     * Sets a name for chat. Accepts only english words, not i18n translations!
     * @param value
     * @return Channel
     */
    public Channel setName(String value) {
        this.name = value;
        return this;
    }

    public String getI18n() {
        return i18n;
    }

    /**
     * Sets a translation name for chat. Accepts only i18n translation key.
     * @param value
     * @return Channel
     */
    public Channel setI18n(String value) {
        this.i18n = value;
        return this;
    }

    public String getTag() {
        return tag;
    }

    /**
     * Channel tag. This parameter means how to call a channel in text message(ex. !hello, or $hello)
     * @param value
     * @return Channel
     */

    public Channel setTag(String value) {
        this.tag = value;
        return this;
    }

    public String getUnFormattedPreText() {
        return preText;
    }

    public String getFormattedPreText() {
        String color = "";
        if(preText.contains("§")) {
            color = preText.split("§[a-zA-Z0-9]")[0];
        }

        return color + "[" + preText + "]";
    }

    /**
     * Channel pre text. Always writes before message (ex. [L] %mesaage%)
     * @param value
     * @return Channel
     */

    public Channel setPreText(String value) {
        this.preText = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Channel description. This parameter outputs when a user joins this channel first time
     * @param value
     * @return Channel
     */

    public Channel setDescription(String value) {
        this.description = value;
        return this;
    }

    public String getRules() {
        return rules;
    }

    /**
     * Channel rules. This parameter outputs after description when a user joins this channel first time
     * @param value
     * @return Channel
     */
    public Channel setRules(String value) {
        this.rules = value;
        return this;
    }

    public boolean isShowChannelInfo() {
        return showChannelInfo;
    }

    /**
     * Toggling channel description/rules.
     * @param value
     * @return Channel
     */
    public Channel setChannelInfoState(boolean value) {
        this.showChannelInfo = value;
        return this;
    }

    public double getChatRange() {
        return range;
    }

    /**
     * Changes chat range. Uses for local channel and global(difference in these chats that local works only on 20 blocks but global works on infinite blocks)
     * @param value
     * @return Channel
     */
    public Channel setChatRange(double value) {
        this.range = value;
        return this;
    }
}
