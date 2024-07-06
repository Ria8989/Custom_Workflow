import React, { useEffect, useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Chip, Divider, FormControl, TableBody, Table, tableCellClasses,  InputLabel, MenuItem, Select, FormLabel, TableCell, TableRow } from '@mui/material';


const ActionSelector = ({ currentTask, action, listTasks, handleChange }) => {
    const [prevList, setPrevList] = useState([]);

    const handleMultiSelectChange = (event) => {
        const { value } = event.target;
        const selectedValues = Array.isArray(value) ? value : [value]; // Ensure value is always an array
    
        setPrevList(selectedValues);
        console.log("prev", selectedValues)
        console.log("next", prevList)
        handleChange(action, selectedValues);
      };

    return (
        <div>
            <Table sx={{
              [`& .${tableCellClasses.root}`]: {
                borderBottom: "none"
              }, maxWidth: "90%", ml:"5%", mr:"5%"
            }}
            aria-label="a dense table"
            size="small"
            >
             <colgroup>
                <col width={"30%"} />
                <col width={"70%"} />
                </colgroup>
            <TableBody>
            <TableRow>
            <TableCell>
            <FormLabel sx={{fontSize:"1.1rem" }}>Action: {action}</FormLabel>
            </TableCell>
            <TableCell>
            <FormControl fullWidth margin="normal">
            <InputLabel>Select Next Task</InputLabel>
            <Select
                name="prevList"
                value={prevList}
                onChange={handleMultiSelectChange}
                multiple
                label="Select Next Task"
                required={true} // Set required conditionally
                sx={{backgroundColor:"white"}}
            >
                {
                listTasks
                .filter(item => item.taskId !== currentTask.taskId)
                .sort((a,b) => b.taskId - a.taskId)
                .map((task) => (
                    <MenuItem key={task.taskId} value={task.taskId}>
                    {task.taskName}
                    </MenuItem>
                ))
                }
            </Select>
            </FormControl>
            </TableCell>
            </TableRow>
            </TableBody>
            </Table>
        </div>
      );
}

export default ActionSelector;