package com.movie.movie_management_server.controller;

import com.movie.movie_management_server.dto.ShowTimeDTO;
import com.movie.movie_management_server.dto.request.ShowTimeRequestDTO;
import com.movie.movie_management_server.service.ShowTimeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/show-time")
public class ShowTimeController {

    private final ShowTimeService showTimeService;

    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addShowTime(@Valid @RequestBody ShowTimeRequestDTO showTimeRequestDTO){
        showTimeService.addShowTime(showTimeRequestDTO);
        return ResponseEntity.ok("Successfully added Show Time");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowTimeDTO>> getAllShowTimes(@RequestParam(value = "date", required = false) String dateStr){
        LocalDate date = (dateStr == null || dateStr.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr);
        return ResponseEntity.ok(showTimeService.getShowTime(date));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTimeDTO> getAllShowTimes(@PathVariable Long id){
        return ResponseEntity.ok(showTimeService.getShowTimeById(id));
    }
}
