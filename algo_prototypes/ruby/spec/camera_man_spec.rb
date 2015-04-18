require './camera_man'

RSpec.describe CameraMan do
  let(:low_bird) { double(altitude: 15) }
  let(:high_bird) { double(altitude: 25) }
  let(:birds) { [low_bird, high_bird] }
  subject { CameraMan.new(birds) }

  it "returns correct max bird altitude" do
    expect(subject.max_bird_altitude).to eq 25
  end

  it "returns correct min bird altitude" do
    expect(subject.min_bird_altitude).to eq 15
  end

  it "returns a correct bird_spread" do
    expect(subject.bird_spread).to eq 10
  end

  it "returns min bird spread if too close together" do
    expect(CameraMan.new([low_bird,low_bird]).bird_spread).to eq CameraMan::MIN_BIRD_SPREAD
  end

  it "initializes with top and bottom matching desired" do
    expect(subject.top).to eq subject.desired_top
    expect(subject.bottom).to eq subject.desired_bottom
  end

  it "has a top half again as high as the bird spread" do
    expect(subject.top).to eq 30
  end

  it "has a bottom half again as low as the bird spread" do
    expect(subject.bottom).to eq 10
  end

  it "has a velocity of zero when already aligned" do
    expect(subject.v_top).to eq 0
    expect(subject.v_bottom).to eq 0
  end

  it "gets higher absolute top velocity when the top moves up" do
    subject
    allow(high_bird).to receive(:altitude).and_return(26)
    expect(subject.v_top).to be > 0
    expect(subject.v_bottom).to be < 0
    expect(subject.v_top.abs).to be > subject.v_bottom.abs
  end

  it "starts moving when stepping" do
    subject
    allow(high_bird).to receive(:altitude).and_return(26)
    subject.step(0)
    subject.step(0.1)
    expect(subject.top).to be > 30
    expect(subject.bottom).to be < 10
  end
end
