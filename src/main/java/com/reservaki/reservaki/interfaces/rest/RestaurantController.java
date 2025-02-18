package com.reservaki.reservaki.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.reservaki.reservaki.application.service.RestaurantService;
import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Restaurant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "API para gerenciamento de restaurantes")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @Operation(summary = "Criar um novo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do restaurante inválidos")
    })
    public ResponseEntity<Restaurant> createRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant created = restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<Restaurant> getRestaurant(
            @Parameter(description = "ID do restaurante") @PathVariable UUID id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping
    @Operation(summary = "Listar todos os restaurantes")
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes recuperada com sucesso")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/cuisine/{type}")
    @Operation(summary = "Buscar restaurantes por tipo de culinária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes por culinária recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum restaurante encontrado para o tipo de culinária")
    })
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(
            @Parameter(description = "Tipo de culinária") @PathVariable String type) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisineType(type));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos")
    })
    public ResponseEntity<Restaurant> updateRestaurant(
            @Parameter(description = "ID do restaurante") @PathVariable UUID id,
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant updated = restaurantService.updateRestaurant(id, restaurantDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<Void> deleteRestaurant(
            @Parameter(description = "ID do restaurante") @PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}