package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.CampaignRequest;
import dev.chinhcd.backend.dtos.requests.ProductRequest;
import dev.chinhcd.backend.dtos.responses.CampaignResponse;
import dev.chinhcd.backend.dtos.responses.MessageResponse;
import dev.chinhcd.backend.service.CampaignServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/campaigns")
@AllArgsConstructor
public class CampaignController {
    private final CampaignServiceImpl campaignService;

    @GetMapping("/all")
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns(){
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CampaignResponse>> getCampaignsByCampaignRequest(@RequestParam Optional<Integer> campId, @RequestParam Optional<Integer> planId, @RequestParam Optional<Integer> prodId){
        CampaignRequest camp = CampaignRequest.builder()
                .campaignId(campId.orElse(null))
                .planId(planId.orElse(null))
                .productRequest(ProductRequest.builder()
                        .productId(prodId.orElse(null))
                        .build())
                .build();
        return ResponseEntity.ok(campaignService.getCampaignsByCampaignRequest(camp));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCampaign(@RequestBody CampaignRequest campaignRequest){
        try{
            CampaignResponse camp = campaignService.addCampaign(campaignRequest);
            return ResponseEntity.ok(camp);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCampaign(@RequestBody CampaignRequest campaignRequest){
        try{
            CampaignResponse camp = campaignService.updateCampaign(campaignRequest);
            return ResponseEntity.ok(camp);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable int id){
        try {
            campaignService.deleteCampaignById(id);
            return ResponseEntity.ok(MessageResponse.builder().message("Deleted successfully").build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
