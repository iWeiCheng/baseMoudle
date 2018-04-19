package com.jiajun.demo.base;

public final class DialogConfig {

    /**
     * 是否允许点击外部使对话框消失
     */
    private final boolean cancelable;

    /**
     * dialog的阴影度
     */
    private final int dimAmount;

    /**
     * 是否允许边界,true widthPercent ,heightPercent将有效果
     */
    private final boolean allowBounds;

    /**
     * dialog宽度为屏幕宽度的百分比
     */
    private final float widthPercent;

    /**
     * dialog宽度为屏幕高度的百分比
     */
    private final float heightPercent;

    /**
     * dialog内容的Gravity
     */
    private final int gravity;

    /**
     * dialog的样式
     */
    private final int style;

    public DialogConfig(Builder builder) {
        cancelable = builder.cancelable;
        dimAmount = builder.dimAmount;
        allowBounds = builder.allowBounds;
        widthPercent = builder.widthPercent;
        heightPercent = builder.heightPercent;
        gravity = builder.gravity;
        style = builder.style;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public int getDimAmount() {
        return dimAmount;
    }

    public boolean isAllowBounds() {
        return allowBounds;
    }

    public float getWidthPercent() {
        return widthPercent;
    }

    public float getHeightPercent() {
        return heightPercent;
    }

    public int getGravity() {
        return gravity;
    }

    public int getStyle() {
        return style;
    }

    public static class Builder {

        /**
         * 是否允许点击外部使对话框消失
         */
        private boolean cancelable;

        /**
         * dialog的阴影度
         */
        private int dimAmount;

        /**
         * 是否允许边界,true widthPercent ,heightPercent将有效果
         */
        private boolean allowBounds;

        /**
         * dialog宽度为屏幕宽度的百分比
         */
        private float widthPercent;

        /**
         * dialog宽度为屏幕高度的百分比
         */
        private float heightPercent;

        /**
         * dialog内容的Gravity
         */
        private int gravity;

        /**
         * dialog的样式
         */
        private int style;

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder dimAmount(int dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder allowBounds(boolean allowBounds) {
            this.allowBounds = allowBounds;
            return this;
        }

        public Builder widthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public Builder heightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder style(int style) {
            this.style = style;
            return this;
        }

        public DialogConfig build() {
            return new DialogConfig(this);
        }
    }

}