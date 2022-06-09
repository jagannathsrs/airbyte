/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers.temporal.scheduling.activities;

import io.airbyte.config.StreamDescriptor;
import io.airbyte.config.persistence.StreamResetPersistence;
import io.airbyte.workers.temporal.exception.RetryableException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StreamResetActivityImpl implements StreamResetActivity {

  private StreamResetPersistence streamResetPersistence;

  @Override
  public void deleteStreamResets(final DeleteStreamResetsInput streamsToDelete) {
    final List<StreamDescriptor> streamDescriptorList = streamsToDelete.getStreamDescriptorList();
    final UUID connectionId = streamsToDelete.getConnectionId();
    try {
      streamResetPersistence.deleteStreamResets(connectionId, streamDescriptorList);
    } catch (final Exception e) {
      throw new RetryableException(e);
    }
  }

}