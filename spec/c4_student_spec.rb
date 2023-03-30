#
# Alexis Webster
#
require 'spec_helper'

describe 'C4 standard board size' do
    it 'detects P1 winning horizontally in row 0' do
        result = test_c4('aabbccdq')
        expect(result).to declare_win_for 1
    end

    it 'detects P1 winning horizontally in row 2' do
        result = test_c4('abcdabcdagbgcgdq')
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning horizontally in row 5' do
        result = test_c4('cdefcdefcdefdcfedcfeacadaegfq')
        expect(result).to declare_win_for 2
    end

    it 'detects P2 winning vertically in column 1' do
        result = test_c4('abababgbq')
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning vertically in column 0' do
        result = test_c4('abababaq')
        expect(result).to declare_win_for 1
    end

    it 'detects P1 winning vertically in column 6' do
        result = test_c4('gcgagbgq')
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning diagonally up to the right starting in row 1' do
        result = test_c4('ccddadeeeegfffffq')
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning diagonally down to the right ending in row 0' do
        result = test_c4('gffeeaeddddq')
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning diagonally up to the right starting in row 2' do
        result = test_c4('aababbabcccgccgdddgdddq')
        expect(result).to declare_win_for 2
    end

    it 'quits before declaring a winner in column 0' do
        result = test_c4('abababq')
        expect(result).to be_abandoned
    end

    it 'quits before declaring a winner in row 2' do
        result = test_c4('abcdabcdagbgcgq')
        expect(result).to be_abandoned
    end

    it 'quits before declaring a diagonal winner' do
        result = test_c4('cddeaeefffq')
        expect(result).to be_abandoned
    end
end


describe 'Connect 4 alternate' do
    it 'detects P2 winning horizontally on a big board' do
        result = test_c4('iaabbccddeeffgq', 3, 9, 7)
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning vertically on a big board' do
        result = test_c4('hahchihihehahq', 16, 16, 7)
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning diagonally up to the right starting in row 3 on a big board' do
        result = test_c4('abbaabbaabccccccddddedddq', 16, 16, 4)
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning horizontally on a small board' do 
        result = test_c4('aabbcq', 4, 4, 3)
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning vertically on a small board' do
        result = test_c4('dcacacq', 4, 4, 3)
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning diagonally down to the right ending in row 0 on a small board' do
        result = test_c4('cbbaacaq', 4, 4, 3)
        expect(result).to declare_win_for 1
    end

    
    it 'detects P2 winning horizontally on a medium board' do 
        result = test_c4('abcdeafbecfdq', 5, 6, 4)
        expect(result).to declare_win_for 2
    end

    it 'detects P1 winning vertically on a medium board' do
        result = test_c4('fafafcfq', 5, 6, 4)
        expect(result).to declare_win_for 1
    end

    it 'detects P2 winning diagonally down to the right ending in row 0 on a medium board' do
        result = test_c4('eeddadccccafq', 5, 6, 4)
        expect(result).to declare_win_for 2
    end

    it 'quits before declaring a winner in column 0' do
        result = test_c4('iaabbccddeeffq', 3, 9, 7)
        expect(result).to be_abandoned
    end
end