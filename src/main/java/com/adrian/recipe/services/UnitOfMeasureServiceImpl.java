package com.adrian.recipe.services;

import com.adrian.recipe.commands.UnitOfMeasureCommand;
import com.adrian.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.adrian.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
        this.uomRepository = uomRepository;
        this.uomToUomCommand = uomToUomCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        Set<UnitOfMeasureCommand> uomCommands = new LinkedHashSet<>();

        uomRepository.findAll().forEach(uom -> uomCommands.add(uomToUomCommand.convert(uom)));

        return uomCommands;
    }
}
