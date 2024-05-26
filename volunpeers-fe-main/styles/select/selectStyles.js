export const SELECT_STYLES = {
  control: (baseStyles) => ({
    ...baseStyles,
    padding: "0.8rem",
    borderRadius: "0.8rem;",
    border: 0,
    boxShadow: "none",
  }),
  option: (styles, { isFocused, isSelected }) => {
    return {
      ...styles,
      backgroundColor: isSelected
        ? "rgb(251 113 133)"
        : isFocused
        ? "rgb(255 228 230)"
        : undefined,
      ":active": {
        ...styles[":active"],
        backgroundColor: isSelected ? "rgb(251 113 133)" : null,
      },
    };
  },
  multiValue: (styles) => {
    return {
      ...styles,
      backgroundColor: "rgb(255 228 230)",
    };
  },
};
