package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.CampaignRequest;
import dev.chinhcd.backend.dtos.responses.CampaignResponse;
import dev.chinhcd.backend.service.CampaignServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/campaigns")
@AllArgsConstructor
public class CampaignController {
    private final CampaignServiceImpl campaignService;

    @GetMapping("/all")
    public ResponseEntity<Set<CampaignResponse>> getAllCampaigns(){
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<CampaignResponse>> getCampaignsByCampaignRequest(@RequestBody CampaignRequest campaignRequest){
        return ResponseEntity.ok(campaignService.getCampaignsByCampaignRequest(campaignRequest));
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
        campaignService.deleteCampaignById(id);
        return ResponseEntity.ok("Delete campaign successfully");
    }
}
