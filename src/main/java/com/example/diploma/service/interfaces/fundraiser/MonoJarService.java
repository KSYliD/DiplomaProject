package com.example.diploma.service.interfaces.fundraiser;

import com.example.diploma.model.fundraiser.Fundraiser;

public interface MonoJarService {
    Fundraiser sendRequest(String jarId, String pc);
}
