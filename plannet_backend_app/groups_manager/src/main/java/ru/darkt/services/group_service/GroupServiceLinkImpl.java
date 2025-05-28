package ru.darkt.services.group_service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GroupServiceLinkImpl implements GroupServiceLink {
}
