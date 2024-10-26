package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.CampaignRequest;
import dev.chinhcd.backend.dtos.responses.CampaignResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.PlanCampaign;

import java.util.Optional;
import java.util.Set;

public interface CampaignService {
    Set<CampaignResponse> getAllCampaigns();

    Set<CampaignResponse> getCampaignsByCampaignRequest(CampaignRequest request);

    CampaignResponse addCampaign(CampaignRequest request) throws DataNotFoundException;

    CampaignResponse updateCampaign(CampaignRequest request) throws DataNotFoundException;

    void deleteCampaignById(int id);

    Optional<PlanCampaign> getCampaignById(int id);
}
