import styled from "styled-components";
// Make sure the path is correct
import { StyledCheckboxProps } from "../../utils/GlobalInterfaces";

export const StyledCheckbox = styled.div<StyledCheckboxProps>`
  width: 20px;
  margin: 0;
  height: 20px;
  background-color: ${(props) => (props.active ? props.theme.colors.blue : "white")};
  display: flex;
  justify-content: center;
  align-items: center;
  border: ${(props) => (props.active ? "none" : `solid 2px ${props.theme.colors.darkGray}`)};
  cursor: pointer;
`;

export const StyledCheckboxBackground = styled.div<StyledCheckboxProps>`
  width: 35px;
  box-sizing: border-box;
  border-radius: 100%;
  height: 35px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  &:hover {
      background-color: ${(props) => (props.active ? "rgba(29, 161, 242, 0.15)" : "rgba(0, 0, 0, 0.07)")}; // Corrected rgba values
  }
`;