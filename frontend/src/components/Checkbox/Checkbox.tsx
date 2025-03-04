import React, { useState } from 'react';
import { StyledCheckbox, StyledCheckboxBackground } from './StyledCheckbox';
import CheckRoundedIcon from '@mui/icons-material/CheckRounded';
import './Checkbox.css';

export const Checkbox: React.FC = () => {
  const [clicked, setClicked] = useState<boolean>(false);

  const toggleCheckbox = () => {
    setClicked(!clicked);
  };

  return (
    <div className='checkbox-container'>
         <StyledCheckboxBackground active={clicked} onClick={toggleCheckbox}>
      <StyledCheckbox active={clicked}>
        {clicked ? (
          <CheckRoundedIcon sx={{ fontSize: 18, color: "white" }} />
        ) : <></>} {/* Use null or a fragment <></> for empty case */}
      </StyledCheckbox>
    </StyledCheckboxBackground>
    </div>
 
  );
};