import { useState, useEffect, act } from "react";
import React, { JSX }  from "react";
import { StyledInputBox,StyledInputLabel } from "./StyledInput";
import { determinValidatedSelectStyle } from "../../utils/DetermineStylesUtils";


interface ValidatedDateSelectorProps{
    style:string;
    valid: boolean;
    name:string;
    dropDown():JSX.Element[],
    dispatcher(name:string,value:string|number|boolean):void;
}

export const ValidatedDateSelector:React.FC<ValidatedDateSelectorProps> = ({style,valid,name,dropDown, dispatcher}) => {

    const [active,setActive] = useState<boolean>(false);
    const [value,setValue] = useState<number>(0);
    const [color,setColor] = useState<string>('gray');

    const changeValue = (e:React.ChangeEvent<HTMLSelectElement>)=> {
        setValue(+e.target.value);
        
        console.log('Dispatch this change to a reducer ');
        console.log('Value : ',e.target.value);

        dispatcher(name.toLowerCase(), +e.target.value);

    }

    const toggleActive = (e:React.FocusEvent<HTMLSelectElement>) => {
        setActive(!active);
    }



    useEffect(()=>{
        setColor(determinValidatedSelectStyle(active,valid))
    },[active,valid,value])

    return (
        <div className={style}>
            <StyledInputBox active={active} valid={valid}>
                <StyledInputLabel color={color} active={true} valid={valid}>
                    {name}
                </StyledInputLabel>
                <select onChange={changeValue} onFocus={toggleActive} onBlur={toggleActive}>
                    {dropDown()}
                </select>

             </StyledInputBox>   
        </div>
    )
}