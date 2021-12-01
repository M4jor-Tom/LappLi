package com.muller.lappli.domain.enumeration;

/**
 * The Color enumeration.
 */
public enum Color {
    NONE("NONE"),
    NATURAL("NAT"),
    WHITE("B"),
    BROWN("M"),
    GREEN("V"),
    YELLOW("J"),
    GREY("G"),
    PINK("RO"),
    BLUE("BL"),
    RED("R"),
    BLACK("N"),
    PURPLE("VIO"),
    PINK_GREY(PINK, GREY),
    RED_BLUE(RED, BLUE),
    WHITE_GREEN(WHITE, GREEN),
    BROWN_GREEN(BROWN, GREEN),
    WHITE_YELLOW(WHITE, YELLOW),
    YELLOW_BROWN(YELLOW, BROWN),
    WHITE_GREY(WHITE, GREY),
    GREY_BROWN(GREY, BROWN),
    WHITE_PINK(WHITE, PINK),
    PINK_BROWN(PINK, BROWN),
    WHITE_BLUE(WHITE, BLUE),
    BROWN_BLUE(BROWN, BLUE),
    WHITE_RED(WHITE, RED),
    BROWN_RED(BROWN, RED),
    WHITE_BLACK(WHITE, BLACK),
    BROWN_BLACK(BROWN, BLACK),
    GREY_GREEN(GREY, GREEN),
    YELLOW_GREY(YELLOW, GREY),
    PINK_GREEN(PINK, GREEN),
    YELLOW_PINK(YELLOW, PINK),
    GREEN_BLUE(GREEN, BLUE),
    YELLOW_BLUE(YELLOW, BLUE),
    GREEN_RED(GREEN, RED),
    YELLOW_RED(YELLOW, RED),
    GREEN_BLACK(GREEN, BLACK),
    YELLOW_BLACK(YELLOW, BLACK),
    GREY_BLUE(GREY, BLUE),
    PINK_BLUE(PINK, BLUE),
    GREY_RED(GREY, RED),
    PINK_RED(PINK, RED),
    GREY_BLACK(GREY, BLACK),
    PINK_BLACK(PINK, BLACK),
    BLUE_BLACK(BLUE, BLACK),
    RED_BLACK(RED, BLACK),
    WHITE_BROWN_BLACK(WHITE, BROWN, BLACK),
    YELLOW_GREEN_BLACK(YELLOW, GREEN, BLACK),
    GREY_PINK_BLACK(GREY, PINK, BLACK),
    RED_BLUE_BLACK(RED, BLUE, BLACK),
    WHITE_GREEN_BLACK(WHITE, GREEN, BLACK),
    BROWN_GREEN_BLACK(BROWN, GREEN, BLACK),
    WHITE_YELLOW_BLACK(WHITE, YELLOW, BLACK),
    YELLOW_BROWN_BLACK(YELLOW, BROWN, BLACK),
    WHITE_GREY_BLACK(WHITE, GREY, BLACK),
    GREY_BROWN_BLACK(GREY, BROWN, BLACK),
    WHITE_PINK_BLACK(WHITE, PINK, BLACK),
    PINK_BROWN_BLACK(PINK, BROWN, BLACK),
    WHITE_BLUE_BLACK(WHITE, BLUE, BLACK),
    BROWN_BLUE_BLACK(BROWN, BLUE, BLACK),
    WHITE_RED_BLACK(WHITE, RED, BLACK),
    BROWN_RED_BLACK(BROWN, RED, BLACK),
    BLACK_WHITE(BLACK, WHITE);

    private String designation;

    private Color(String designation) {
        setDesignation(designation);
    }

    private Color(Color... colors) {
        String designation = "";

        int colorsCount = colors.length;

        int currentColorIndex = 1;
        for (Color color : colors) {
            designation = designation + color.getDesignation();

            if (colorsCount < currentColorIndex++) {
                designation = designation + "/";
            }
        }

        setDesignation(designation);
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }
}
