#!/usr/bin/env ruby

class CameraMan
  MAX_ZOOM_VELOCITY = 5 # m/s Maximum rate at which the top or bottom of the camera will move
  CAMERA_LAG = 0.5 # s Number of seconds that it would take to get the birds to the right spots on the screen
  MIN_BIRD_SPREAD = 5 # m - We don't zoom any closer than as if the birds were N meters apart

  attr_reader :birds

  attr_accessor :bottom # altitude in meters
  attr_accessor :top # altitude in meters
  attr_accessor :prev_timestamp

  def initialize(birds)
    @birds = birds
    self.top = desired_top
    self.bottom = desired_bottom
  end

  def calculate_zoom_velocity(current_altitude, desired_altitude)
    change = desired_altitude - current_altitude
    candidate_v = change / CAMERA_LAG
    if candidate_v.abs > MAX_ZOOM_VELOCITY
      MAX_ZOOM_VELOCITY * (candidate_v > 0 ? 1 : -1)
    else
      candidate_v
    end
  end

  def step(timestamp)
    if prev_timestamp
      elapsed = timestamp - prev_timestamp
      self.top = top + v_top * elapsed
      self.bottom = bottom + v_bottom * elapsed
    end
    self.prev_timestamp = timestamp
  end

  def v_top
    calculate_zoom_velocity(top, desired_top)
  end

  def v_bottom
    calculate_zoom_velocity(bottom, desired_bottom)
  end

  def desired_bottom
    min_bird_altitude - bird_spread / 2.0
  end

  def desired_range
    desired_top - desired_bottom
  end

  def desired_top
    bird_spread / 2.0 + max_bird_altitude
  end

  def bird_spread
    actual_bird_spread = max_bird_altitude - min_bird_altitude
    actual_bird_spread > MIN_BIRD_SPREAD ? actual_bird_spread : MIN_BIRD_SPREAD
  end

  def max_bird_altitude
    birds.map(&:altitude).max
  end

  def min_bird_altitude
    birds.map(&:altitude).min
  end
end
