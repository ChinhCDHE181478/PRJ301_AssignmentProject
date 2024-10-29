package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.CampaignRequest;
import dev.chinhcd.backend.dtos.responses.CampaignResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.PlanCampaign;

import java.util.List;
import java.util.Optional;

public interface CampaignService {
    List<CampaignResponse> getAllCampaigns();

    List<CampaignResponse> getCampaignsByCampaignRequest(CampaignRequest request);

    CampaignResponse addCampaign(CampaignRequest request) throws DataNotFoundException;

    CampaignResponse updateCampaign(CampaignRequest request) throws DataNotFoundException;

    void deleteCampaignById(int id);

    Optional<PlanCampaign> getCampaignById(int id);
}
