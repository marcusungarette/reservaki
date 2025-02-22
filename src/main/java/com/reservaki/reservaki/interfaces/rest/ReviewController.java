package com.reservaki.reservaki.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.reservaki.reservaki.application.service.ReviewService;
import com.reservaki.reservaki.application.dto.ReviewDTO;
import com.reservaki.reservaki.domain.entity.Review;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Endpoints para gerenciamento de avaliações")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Criar uma nova avaliação")
    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar avaliações de um restaurante")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> getRestaurantReviews(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(reviewService.getRestaurantReviews(restaurantId));
    }

    @Operation(summary = "Obter média das avaliações de um restaurante")
    @GetMapping("/restaurant/{restaurantId}/rating")
    public ResponseEntity<Double> getRestaurantAverageRating(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(reviewService.getRestaurantAverageRating(restaurantId));
    }
}