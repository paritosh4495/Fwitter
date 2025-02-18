import ClearRounded from '@mui/icons-material/ClearRounded';
import ArrowBackRounded from '@mui/icons-material/ArrowBackRounded';

import { JSX } from 'react'

export  const displayIcon = (step : number) : JSX.Element => {

    switch(step) {

        case 1: 
            return <ClearRounded sx={{fontSize:25}} />
        case 2:
            return <ArrowBackRounded sx={{fontSize:25}}/>
        case 3:
            return <ArrowBackRounded sx={{fontSize:25}}/>
        case 4:
            return <></>
        case 5:
            return <ArrowBackRounded sx={{fontSize:25}}/>
        case 6:
            return <></>
        default:
            return <></>


    }

}