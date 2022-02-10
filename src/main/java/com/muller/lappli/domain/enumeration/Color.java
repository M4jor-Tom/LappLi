package com.muller.lappli.domain.enumeration;

/**
 * The Color enumeration.
 *
 * It follow the DIN-47100 norma/strandard used in Muller,
 * with the "NATURAL" Color added in the beggining of that
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

    private Color[] colors;

    /**
     * Creates an atomic color
     *
     * @param designation the designation
     */
    private Color(String designation) {
        setDesignation(designation);
        setColors(null);
    }

    /**
     * Creates a composite color
     *
     * @param colors the composing colors
     */
    private Color(Color... colors) {
        setDesignation(null);
        setColors(colors);
    }

    /**
     * Tells if the color is composite (true), meaning
     * composed of other colors, or atomic (false), meaning
     * not composed of anything.
     *
     * @return a Boolean
     */
    public Boolean isComposite() {
        return getColors() != null;
    }

    /**
     * The designation of the color.
     *
     * It will be merged from composing colors'
     * designations if {@link Color#getColors()} is null,
     * in the case of a composite Color, then.
     *
     * Otherwise, in the case of an atomic color, it
     * will just read its value.
     *
     * @return the designation
     */
    public String getDesignation() {
        if (isComposite()) {
            return this.designation;
        }

        String designation = "";

        Long currentColorIndex = Long.valueOf(1);
        for (Color color : getColors()) {
            String separator = currentColorIndex++ == Long.valueOf(1) ? "" : "•";

            designation = designation + separator + color.getDesignation();
        }

        return designation;
    }

    private void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * The list of the composing colors.
     *
     * In the case of a composite color,
     * it will return each composing color.
     *
     * In the case of an atomic color,
     * it will return null.
     *
     * @return the list of colors
     */
    public Color[] getColors() {
        return this.colors;
    }

    private void setColors(Color[] colors) {
        this.colors = colors;
    }
}
